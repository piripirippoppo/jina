//
//  Document.swift
//  assign4-map
//
//  Created by jina kang on 4/24/20.
//  Copyright © 2020 jina kang. All rights reserved.
//

import UIKit
import CoreData

class MapDocument: UIDocument {
    var container: MapContainer?
    
    override func contents(forType typeName: String) throws -> Any {
        // Encode your document with an instance of NSData or NSFileWrapper
        return container?.json ?? Data()
    }
    
    override func load(fromContents contents: Any, ofType typeName: String?) throws {
        if let data = contents as? Data {
            container = MapContainer(json: data)
        }
    }
}
