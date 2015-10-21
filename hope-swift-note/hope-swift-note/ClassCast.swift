//
//  ClassCast.swift
//  SwiftOOP
//
//  Created by Hope6537 on 15-1-26.
//  Copyright (c) 2015年 Hope6537. All rights reserved.
//

import Foundation

class Fruit{
    let placeOfOrigninal:String
    init(placeOfOrigninal:String){
        self.placeOfOrigninal = placeOfOrigninal;
    }
}

class Apple : Fruit{
    func description(){
        print("apple! " + placeOfOrigninal);
    }
}
class Orange : Fruit{
    func description(){
        print("orange! " + placeOfOrigninal);
    }
}

func testClassCast(){
    
    let f1 = Apple(placeOfOrigninal: "aa");
    let f2 = Apple(placeOfOrigninal: "bb");
    let f3 = Orange(placeOfOrigninal: "cc");
    let f4 = Orange(placeOfOrigninal: "dd");
    let f5 = Apple(placeOfOrigninal: "ff");
    
    let Basket = [f1,f2,f3,f4,f5]
    for fruit in Basket {
        //as？是可选下转型，而as是强制下转型
        if let apple  = fruit as? Apple{
            apple.description();
        }
        else if let orange = fruit as? Orange {
            orange.description();
        }
    }
    
    var appleCount = 0;
    var orangeCount = 0;
    for fruit in Basket {
        if fruit is Apple{
            appleCount++;
        }
        else if fruit is Orange {
            orangeCount++;
        }
    }

}

