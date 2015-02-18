package org.hope6537.test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class JavaGenericTest<T> {

    public static void main(String[] args) {
        Logger logger = LoggerFactory.getLogger("main");
        logger.error("error");
        logger.warn("warn");
        logger.info("info");
        logger.debug("debug");

    }


}
