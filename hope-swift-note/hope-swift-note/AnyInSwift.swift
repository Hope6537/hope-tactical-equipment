//
//  AnyInSwift.swift
//  SwiftOOP
//
//  Created by Hope6537 on 15-1-27.
//  Copyright (c) 2015年 Hope6537. All rights reserved.
//

import Foundation

class Evil{
    var name = "";
}
class Angel{
    var name = "";
}
class Folk{
    var name = "";
}

struct TestStruct{
    var name : String;
    var value : String;
}

func testAnyObject(){
    let test1  = Evil();
    let test2  = Angel();
    let test3  = Folk();
    var Buddhism = [AnyObject]();
    //AnyObject
    Buddhism = [test1,test2,test3];
}
func testAny(){
    //any 可以存入任何数据
    var things = [Any]()
    things.append(0);
    things.append("dwdq");
    things.append(TestStruct(name:"sdas", value : "Dsad"));
}