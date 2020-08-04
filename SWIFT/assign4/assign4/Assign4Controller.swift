//
//  DocumentViewController.swift
//  assign4
//
//  Created by jina kang on 4/24/20.
//  Copyright © 2020 jina kang. All rights reserved.
//

import UIKit

class Assign4Controller: UIViewController, UIGestureRecognizerDelegate {
    
    @IBOutlet weak var documentNameLabel: UILabel!
    
    var document: Assign4Document?
    var single: UITapGestureRecognizer!
    var double: UITapGestureRecognizer!
    
    func gestureRecognizer(_ gestureRecognizer: UIGestureRecognizer, shouldRequireFailureOf otherGestureRecognizer: UIGestureRecognizer) -> Bool {
        return (gestureRecognizer == single) && (otherGestureRecognizer == double)
    }
    
    override func viewWillAppear(_ animated: Bool) {
        super.viewWillAppear(animated)
        
        // Access the document
        document?.open(completionHandler: { (success) in
            if success {
                if let assignView = self.view as? Assign4View {
                    assignView.document = self.document
                    assignView.setNeedsDisplay()
                    
                }
                self.single = UITapGestureRecognizer(target: self, action: #selector(self.singleTap))
                self.single.numberOfTapsRequired = 1
                self.single.numberOfTouchesRequired = 1
                self.view.addGestureRecognizer(self.single)
                self.single.delegate = self

                self.double = UITapGestureRecognizer(target: self, action: #selector(self.doubleTap))
                self.double.numberOfTapsRequired = 2
                self.double.numberOfTouchesRequired = 1
                self.view.addGestureRecognizer(self.double)
                
            } else {
                // Make sure to handle the failed import appropriately, e.g., by presenting an error message to the user.
            }
        })
    }
    
    @objc func singleTap(recog: UITapGestureRecognizer) {
        let loc = recog.location(in: view)
        if let container = document?.container {
            container.assigns.append(Assign(c: loc, r: 100))
            view.setNeedsDisplay()
            document?.updateChangeCount(.done)
        }
    }
    
    @objc func doubleTap(recog: UITapGestureRecognizer) {
        dismiss(animated: true) {
            self.document?.close(completionHandler: nil)
        }
    }
    
    @IBAction func dismissDocumentViewController() {
        dismiss(animated: true) {
            self.document?.close(completionHandler: nil)
        }
    }
}
