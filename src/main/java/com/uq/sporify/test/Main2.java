package com.uq.sporify.test;

public class Main2 {
    public static void main(String[] args) {
        String url = "https://www.youtube.com/watch?v=S9bCLPwzSC0";
        String urlAux = "";

        //Necesito un recorrido de la url al reves y que me almacene en ulFinal hasta que encuentre el = o / y que me devuelva el string
        for (int i = url.length()-1; i >= 0; i--) {
            if (url.charAt(i) == '=' || url.charAt(i) == '/') {
                i=0;
            }else {
                urlAux += url.charAt(i);
            }
        }
        StringBuilder invertido = new StringBuilder(urlAux);
        invertido = invertido.reverse();
        String finalUrl = "https://www.youtube.com/embed/"+ invertido + "?autoplay=1";
        System.out.println(finalUrl);
    }
}
