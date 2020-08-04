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

    override func viewDidLoad() {
        super.viewDidLoad()
        
        let root = Database.database().reference()
        root.child("urls").childByAutoId().setValue(["name": "Meditation Sheep",
        "url": "https://sedna.cs.umd.edu/436clips/vids/sheep.mp4"])
    }


}

