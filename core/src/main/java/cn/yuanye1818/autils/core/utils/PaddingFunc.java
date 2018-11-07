package cn.yuanye1818.autils.core.utils;

import android.view.View;

public class PaddingFunc {

    public static int l(View v) {
        if (v == null)
            return -1;
        return v.getPaddingLeft();
    }

    public static int t(View v) {
        if (v == null)
            return -1;
        return v.getPaddingTop();
    }

    public static int r(View v) {
        if (v == null)
            return -1;
        return v.getPaddingRight();
    }

    public static int b(View v) {
        if (v == null)
            return -1;
        return v.getPaddingBottom();
    }

    protected static void p(View v, int l, int t, int r, int b) {
        if (v == null)
            return;
        v.setPadding(l, t, r, b);
    }

    public static void l(View v, int l) {
        p(v, l, t(v), r(v), b(v));
    }

    public static void lt(View v, int l, int t) {
        p(v, l, t, r(v), b(v));
    }

    public static void ltr(View v, int l, int t, int r) {
        p(v, l, t, r, b(v));
    }

    public static void ltrb(View v, int l, int t, int r, int b) {
        p(v, l, t, r, b);
    }

    public static void ltb(View v, int l, int t, int b) {
        p(v, l, t, r(v), b);
    }

    public static void lr(View v, int l, int r) {
        p(v, l, t(v), r, b(v));
    }

    public static void lrb(View v, int l, int r, int b) {
        p(v, l, t(v), r, b);
    }

    public static void lb(View v, int l, int b) {
        p(v, l, t(v), r(v), b);
    }

    public static void t(View v, int t) {
        p(v, l(v), t, r(v), b(v));
    }

    public static void tr(View v, int t, int r) {
        p(v, l(v), t, r, b(v));
    }

    public static void trb(View v, int t, int r, int b) {
        p(v, l(v), t, r, b);
    }

    public static void tb(View v, int t, int b) {
        p(v, l(v), t, r(v), b);
    }

    public static void r(View v, int r) {
        p(v, l(v), t(v), r, b(v));
    }

    public static void rb(View v, int r, int b) {
        p(v, l(v), t(v), r, b);
    }

    public static void b(View v, int b) {
        p(v, l(v), t(v), r(v), b);
    }

}
