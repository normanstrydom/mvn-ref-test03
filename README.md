# mvn-ref-test03

##  Overview

##  Reference projects

*   [https://github.com/normanstrydom/mvn-ref-test01 - Simple library](https://github.com/normanstrydom/mvn-ref-test01)
*   [https://github.com/normanstrydom/mvn-ref-test02 - User of simple library](https://github.com/normanstrydom/mvn-ref-test02)
*   [https://github.com/normanstrydom/mvn-ref-test03 - Web app referencing test01 and 02](https://github.com/normanstrydom/mvn-ref-test03)
*   [https://github.com/normanstrydom/mvn-ref-test-release-action - Action to standardise the release](https://github.com/normanstrydom/mvn-ref-test-release-action)

##  Code setup

##

*   pom
*   


mvn help:evaluate -Dexpression='project.version' -q -DforceStdout

on:
  workflow_dispatch:
    inputs:
      releaseVersion:
        description: 'Release version'
        required: false
      developmentVersion:
        description: 'Development version'
        required: false

jobs:
  release:
    runs-on: ubuntu-latest
    steps:
      - uses: actions/checkout@v7

      - name: Read POM version
        id: read_pom
        run: |
          pv=$(mvn -q -Dexec.executable=echo -Dexec.args='${project.version}' --non-recursive \
            org.codehaus.mojo:exec-maven-plugin:3.1.0:exec)
          echo "pom_version=$pv" >> $GITHUB_OUTPUT

      - name: Decide versions
        id: decide
        run: |
          # choose releaseVersion input if provided, otherwise use pom version
          if [ -n "${{ github.event.inputs.releaseVersion }}" ]; then
            echo "release=${{ github.event.inputs.releaseVersion }}" >> $GITHUB_OUTPUT
          else
            echo "release=${{ steps.read_pom.outputs.pom_version }}" >> $GITHUB_OUTPUT
          fi

          # choose developmentVersion input if provided, otherwise compute a default
          if [ -n "${{ github.event.inputs.developmentVersion }}" ]; then
            echo "development=${{ github.event.inputs.developmentVersion }}" >> $GITHUB_OUTPUT
          else
            # example: append -SNAPSHOT or bump version — adjust to your policy
            dev="${{ steps.read_pom.outputs.pom_version }}-SNAPSHOT"
            echo "development=$dev" >> $GITHUB_OUTPUT
          fi

      - name: Show chosen versions
        run: |
          echo "release = ${{ steps.decide.outputs.release }}"
          echo "development = ${{ steps.decide.outputs.development }}"