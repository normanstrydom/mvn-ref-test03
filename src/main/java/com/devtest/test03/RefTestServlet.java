package com.devtest.test03;

import com.devtest.test01.mathfcns.MathFcns;
import com.devtest.test01.stringfcns.StringFcns;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "RefTestServlet", urlPatterns = { "/ref-test " })
public class RefTestServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        System.out.println("2+3=" + MathFcns.add(2, 3));
        com.devtest.test02.App.main(null);
        System.out.println("reverse 'hello' = " + StringFcns.reverse("hello"));
        resp.setContentType("text/plain;charset=UTF-8");
        resp.getWriter().write("Ref test done!");
    }
}
