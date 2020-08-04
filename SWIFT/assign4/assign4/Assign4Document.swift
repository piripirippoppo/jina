//
//  Document.swift
//  assign4
//
//  Created by jina kang on 4/24/20.
//  Copyright © 2020 jina kang. All rights reserved.
//

import UIKit

class Assign4Document: UIDocument {
    var container: Assign4Container?
    
    override func contents(forType typeName: String) throws -> Any {
        // Encode your document with an instance of NSData or NSFileWrapper
        return container?.json ?? Data()
    }
    
    override func load(fromContents contents: Any, ofType typeName: String?) throws {
        if let data = contents as? Data {
            container = Assign4Container(json: data)
        }
    }
}
