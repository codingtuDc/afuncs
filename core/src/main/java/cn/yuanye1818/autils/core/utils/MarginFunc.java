package cn.yuanye1818.autils.core.utils;

import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;

public class MarginFunc {

    private static ViewGroup.MarginLayoutParams lp(View v) {
        return (ViewGroup.MarginLayoutParams) v.getLayoutParams();
    }

    public static int l(View v) {
        if (v == null)
            return -1;
        return lp(v).leftMargin;
    }

    public static int t(View v) {
        if (v == null)
            return -1;
        return lp(v).topMargin;
    }

    public static int r(View v) {
        if (v == null)
            return -1;
        return lp(v).rightMargin;
    }

    public static int b(View v) {
        if (v == null)
            return -1;
        return lp(v).bottomMargin;
    }

    public static void l(View v, int l) {
        if (v == null)
            return;
        ViewGroup.MarginLayoutParams lp = lp(v);
        lp.leftMargin = l;
        v.setLayoutParams(lp);
    }

    public static void lt(View v, int l, int t) {
        if (v == null)
            return;
        ViewGroup.MarginLayoutParams lp = lp(v);
        lp.leftMargin = l;
        lp.topMargin = t;
        v.setLayoutParams(lp);
    }

    public static void ltr(View v, int l, int t, int r) {
        if (v == null)
            return;
        ViewGroup.MarginLayoutParams lp = lp(v);
        lp.leftMargin = l;
        lp.topMargin = t;
        lp.rightMargin = r;
        v.setLayoutParams(lp);
    }

    public static void ltrb(View v, int l, int t, int r, int b) {
        if (v == null)
            return;
        ViewGroup.MarginLayoutParams lp = lp(v);
        lp.leftMargin = l;
        lp.topMargin = t;
        lp.rightMargin = r;
        lp.bottomMargin = b;
        v.setLayoutParams(lp);
    }

    public static void ltb(View v, int l, int t, int b) {
        if (v == null)
            return;
        ViewGroup.MarginLayoutParams lp = lp(v);
        lp.leftMargin = l;
        lp.topMargin = t;
        lp.bottomMargin = b;
        v.setLayoutParams(lp);
    }

    public static void lr(View v, int l, int r) {
        if (v == null)
            return;
        ViewGroup.MarginLayoutParams lp = lp(v);
        lp.leftMargin = l;
        lp.rightMargin = r;
        v.setLayoutParams(lp);
    }

    public static void lrb(View v, int l, int r, int b) {
        if (v == null)
            return;
        ViewGroup.MarginLayoutParams lp = lp(v);
        lp.leftMargin = l;
        lp.rightMargin = r;
        lp.bottomMargin = b;
        v.setLayoutParams(lp);
    }

    public static void lb(View v, int l, int b) {
        if (v == null)
            return;
        ViewGroup.MarginLayoutParams lp = lp(v);
        lp.leftMargin = l;
        lp.bottomMargin = b;
        v.setLayoutParams(lp);
    }

    public static void t(View v, int t) {
        if (v == null)
            return;
        ViewGroup.MarginLayoutParams lp = lp(v);
        lp.topMargin = t;
        v.setLayoutParams(lp);
    }

    public static void tr(View v, int t, int r) {
        if (v == null)
            return;
        ViewGroup.MarginLayoutParams lp = lp(v);
        lp.topMargin = t;
        lp.rightMargin = r;
        v.setLayoutParams(lp);
    }

    public static void trb(View v, int t, int r, int b) {
        if (v == null)
            return;
        ViewGroup.MarginLayoutParams lp = lp(v);
        lp.topMargin = t;
        lp.rightMargin = r;
        lp.bottomMargin = b;
        v.setLayoutParams(lp);
    }

    public static void tb(View v, int t, int b) {
        if (v == null)
            return;
        ViewGroup.MarginLayoutParams lp = lp(v);
        lp.topMargin = t;
        lp.bottomMargin = b;
        v.setLayoutParams(lp);
    }

    public static void r(View v, int r) {
        if (v == null)
            return;
        ViewGroup.MarginLayoutParams lp = lp(v);
        lp.rightMargin = r;
        v.setLayoutParams(lp);
    }

    public static void rb(View v, int r, int b) {
        if (v == null)
            return;
        ViewGroup.MarginLayoutParams lp = lp(v);
        lp.rightMargin = r;
        lp.bottomMargin = b;
        v.setLayoutParams(lp);
    }

    public static void b(View v, int b) {
        if (v == null)
            return;
        ViewGroup.MarginLayoutParams lp = lp(v);
        lp.bottomMargin = b;
        v.setLayoutParams(lp);
    }

}
