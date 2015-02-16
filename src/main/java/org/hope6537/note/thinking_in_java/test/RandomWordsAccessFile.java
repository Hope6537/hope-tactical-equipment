package org.hope6537.note.thinking_in_java.test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.TimeUnit;

class Counting implements Runnable {
    private static int seconds;

    @Override
    public void run() {
        System.out.println("Running");
        while (!Thread.interrupted()) {
            System.out.println("Now Running at " + seconds + "seconds");
            try {
                TimeUnit.SECONDS.sleep(10);
                seconds += 10;
            } catch (InterruptedException e) {
            }
        }
        System.out.println("Stop");
    }
}

class Exec implements Runnable {
    private static final int BSIZE = 52428800;
    private static final String[] words = "All rights reserved. No part of this book may be reproduced, stored in a retrieval system, or transmitted in any form or by any means, without the prior written permission of the publisher, except in the case of brief quotations embedded in critical articles or reviews. Every effort has been made in the preparation of this book to ensure the accuracy of the information presented. However, the information contained in this book is sold without warranty, either express or implied. Neither the authors, nor Packt Publishing, and its dealers and distributors will be held liable for any damages caused or alleged to be caused directly or indirectly by this book. Packt Publishing has endeavored to provide trademark information about all of the companies and products mentioned in this book by the appropriate use of capitals. However, Packt Publishing cannot guarantee the accuracy of this information."
            .split(" ");
    private volatile File file;

    public Exec(File file, ArrayList<String> list) {
        super();
        this.file = file;
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    @Override
    public void run() {

        try {
            FileChannel in = new FileInputStream(file).getChannel();
            FileChannel out = new FileOutputStream(file).getChannel();
            ByteBuffer buffer = ByteBuffer.allocate(BSIZE);
            synchronized (file) {
                Random rand = new Random(System.nanoTime());
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < 100000; i++) {
                    sb.append("[INFO] ");
                    sb.append(DateFormat_Jisuan.createNowTime());
                    sb.append("+");
                    sb.append(System.currentTimeMillis());
                    sb.append("+");
                    sb.append(System.nanoTime());
                    sb.append(" Log: START[");
                    for (int j = 0; j < 52; j++) {
                        sb.append(words[rand.nextInt(88)] + " ");
                    }
                    sb.append("]END");
                    sb.append("\r\n");
                }
                buffer.flip();
                out.write(buffer);
                out.write(ByteBuffer.wrap(sb.toString().getBytes()));
            }
            System.out.println("Write Finished");
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}

public class RandomWordsAccessFile {
    private static final int BSIZE = 52428800;
    private static final String[] words = "All rights reserved. No part of this book may be reproduced, stored in a retrieval system, or transmitted in any form or by any means, without the prior written permission of the publisher, except in the case of brief quotations embedded in critical articles or reviews. Every effort has been made in the preparation of this book to ensure the accuracy of the information presented. However, the information contained in this book is sold without warranty, either express or implied. Neither the authors, nor Packt Publishing, and its dealers and distributors will be held liable for any damages caused or alleged to be caused directly or indirectly by this book. Packt Publishing has endeavored to provide trademark information about all of the companies and products mentioned in this book by the appropriate use of capitals. However, Packt Publishing cannot guarantee the accuracy of this information. In a typical installation, Hadoop is the heart of a complex flow of data. Data is often collected from many disparate systems. This data is then imported into the Hadoop Distributed File System (HDFS). Next, some form of processing takes place using MapReduce or one of the several languages built on top of MapReduce (Hive, Pig, Cascading, and so on). Finally, the filtered, transformed, and aggregated results are exported to one or more external systems. For a more concrete example, a large website may want to produce basic analytical data about its hits. Weblog data from several servers is collected and pushed into HDFS. A MapReduce job is started, which runs using the weblogs as its input. The weblog data is parsed, summarized, and combined with the IP address geolocation data. The output produced shows the URL, page views, and location data by each cookie. This report is exported into a relational database. Ad hoc queries can now be run against this data. Analysts can quickly produce reports of total unique cookies present, pages with the most views, breakdowns of visitors by region, or any other rollup of this data. The recipes in this chapter will focus on the process of importing and exporting data to and from HDFS. The sources and destinations include the local filesystem, relational databases, NoSQL databases, distributed databases, and other Hadoop clusters"
            .split(" ");

    public static void main(String[] args) throws InterruptedException {
        for (int u = 0; u < 100; u++) {
            File file = new File("G:\\FuckData\\Test" + u + ".log4hope6537");
            if (!file.exists()) {
                try {
                    file.createNewFile();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
            try {
                FileChannel in = new FileInputStream(file).getChannel();
                FileChannel out = new FileOutputStream(file).getChannel();
                ByteBuffer buffer = ByteBuffer.allocate(BSIZE);
                synchronized (file) {
                    Random rand = new Random(System.nanoTime());
                    StringBuilder sb = new StringBuilder();
                    for (int i = 0; i < 200000; i++) {
                        sb.append("[INFO] ");
                        sb.append(DateFormat_Jisuan.createNowTime());
                        sb.append("+");
                        sb.append(System.currentTimeMillis());
                        sb.append("+");
                        sb.append(System.nanoTime());
                        sb.append(" Log: START[");
                        for (int j = 0; j < 52; j++) {
                            sb.append(words[rand.nextInt(370)] + " ");
                        }
                        sb.append("]END");
                        sb.append("\r\n");
                    }
                    buffer.flip();
                    out.write(buffer);
                    out.write(ByteBuffer.wrap(sb.toString().getBytes()));
                    in.close();
                    out.close();
                }
                System.out.println("File " + u + " has Write Finished");
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

    }
}
