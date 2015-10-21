//
//  InnerClass.swift
//  SwiftOOP
//
//  Created by Hope6537 on 15-1-27.
//  Copyright (c) 2015年 Hope6537. All rights reserved.
//

import Foundation

//就是Java的内部类

class OutClass{
    var name : String = "out";
    class InnerClass {
        var name : String = "in";
    }
    var innerClass = InnerClass();
}

func testInnerClass(){
    let test = OutClass();
    print(test.name);
    print(test.innerClass.name);
}