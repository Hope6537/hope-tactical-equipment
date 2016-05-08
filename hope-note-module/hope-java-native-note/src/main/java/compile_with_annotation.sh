#!/usr/bin/env bash
javac org/hope6537/note/jvm/annotation/processor/NameChecker.java;
javac org/hope6537/note/jvm/annotation/processor/NameCheckProcessor.java;
javac -processor org.hope6537.note.jvm.annotation.processor.NameCheckProcessor org/hope6537/note/jvm/annotation/processor/BADLY_NAME_CODE.java