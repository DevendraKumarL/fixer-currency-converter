package com.example.devendra.Fixer;

/**
 * Created by dkumarl on 2/25/2018.
 */

public class Currencies {

    public static enum Symbol {
        USD("USD"), INR("INR");

        private String value;

        Symbol(String value) {
            this.value = value;
        }

        @Override
        public String toString() {
            return this.value;
        }
    }

}
