//
//  URLController.swift
//  assign5
//
//  Created by jina kang on 5/2/20.
//  Copyright © 2020 jina kang. All rights reserved.
//

import UIKit
import Firebase
import AVKit

//struct KeyValue: Comparable {
//    static func < (lhs: KeyValue, rhs: KeyValue) -> Bool {
//        return lhs.val < rhs.val
//     }
//
//    var key: String
//    var val: String
//}
//struct KeyValue : Comparable {
//    var key: String
//    var val: [String]
//}
class URLController: UIViewController   {
    var urlList: [String] = []
    var nameList: [String] = []
    var data: [String: String] = [:]
    var rootRef: DatabaseReference!
    @IBOutlet weak var nameText: UITextField!
    @IBOutlet weak var urlText: UITextField!
    @IBOutlet weak var upload: UIButton!
    
    override func viewDidLoad() {
        super.viewDidLoad()
        rootRef = Database.database().reference()
        rootRef.child("urls").childByAutoId().setValue(nil)
        //rootRef.child("seen").childByAutoId().setValue(nil)
    }
    @IBAction func createName(_ sender: UITextField) {
        nameText.text = sender.text!
    }
    @IBAction func createURL(_ sender: UITextField) {
        urlText.text = sender.text!
    }
    
    @IBAction func reloadVideo(_ sender: UIButton) {
        data = ["name": "\(nameText.text!)", "url": "\(urlText.text!)"]
        rootRef.child("urls").childByAutoId().setValue(data)
    }
    /*
    // MARK: - Navigation

    // In a storyboard-based application, you will often want to do a little preparation before navigation
    override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
        // Get the new view controller using segue.destination.
        // Pass the selected object to the new view controller.
    }
    */
}
