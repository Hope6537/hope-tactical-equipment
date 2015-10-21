//
//  testTypeConstraint.swift
//  SwiftOOP
//
//  Created by Hope6537 on 15-4-8.
//  Copyright (c) 2015年 Hope6537. All rights reserved.
//

import Foundation

class FruitSimple{

    var name : String{
        return "Furit";
    }
}

class AppleSimple : FruitSimple{

    override var name: String{
        return "Apple";
    }
}

func isEqual<V:Equatable,R:FruitSimple>(left:V,right:V,result:R) -> Bool{
    if left == right{
        print("true \(result.name)");
        return true;
    }else{
        print("false \(result.name)");
        return false;
    }
}

func testTypeConstraint(){
    print(isEqual("asdasd",right: "asdasd",result: AppleSimple()));
}
