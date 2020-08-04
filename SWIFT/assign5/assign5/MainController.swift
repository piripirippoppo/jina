//
//  ViewController.swift
//  assign5
//
//  Created by jina kang on 5/2/20.
//  Copyright © 2020 jina kang. All rights reserved.
//

import UIKit
import Firebase

class MainController: UIViewController {

    var url = ""
    var like = 0
    var seen = 0
    var nameList: [String] = []

    override func viewDidLoad() {
        super.viewDidLoad()
     
        let root = Database.database().reference()
        
        root.observe(.value, with: { (snapshot) in
            self.nameList = []
            for child in snapshot.children {
                if let data = child as? DataSnapshot {
                   // nameList = "\(data.value!)".split(separator: ";")
                }
            }
        })
    }
}

