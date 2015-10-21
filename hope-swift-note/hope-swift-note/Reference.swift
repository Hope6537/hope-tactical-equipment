//
//  Reference.swift
//  SwiftOOP
//
//  Created by Hope6537 on 15-1-26.
//  Copyright (c) 2015年 Hope6537. All rights reserved.
//

import Foundation


//闭环的强引用，即发生内存泄露的元凶
class Male{
    let name : String;
    //   使用强引用
    //   var girl : Female?;
    //   使用弱引用
    //    weak var girl : Female?;
    //    使用无主引用 即主人死亡之后 该实例自动销毁 或者说计数－1
    //    即girl死后 boy也殉情了
    unowned var girl : Female;
    //  在无主引用中时，构造器必须给定无主引用变量的值
    //    init(name : String){
    //        self.name = name;
    //    }
    init(name : String , girl : Female){
        self.name = name;
        self.girl = girl;
    }
    deinit{
        print("die male");
    }
}

class Female {
    let name : String;
    weak var boy : Male?;
    init (name : String){
        self.name = name;
    }
    deinit{
        print("die fe");
    }
}
func testReference(){
    //出现了内存泄露
    var woman : Female? = Female(name:"female");
    let man : Male? = Male(name: "male",girl:woman!);
    //在无主引用中要使用！来进行强制解封来使用里面的值
    //man!.girl =  woman;
    
    woman!.boy = man;
    print("deinit");
    //无主引用中 girl死了boy也会跟着死
    //man = nil;
    woman = nil;
    print("finish");
}
