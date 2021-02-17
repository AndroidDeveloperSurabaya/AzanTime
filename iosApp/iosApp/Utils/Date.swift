//
//  Date.swift
//  iosApp
//
//  Created by Proxima Centauri on 27/01/21.
//  Copyright © 2021 orgName. All rights reserved.
//

import Foundation

extension Date {
    func currentTimeMillis() -> Int64 {
        return Int64((self.timeIntervalSince1970))
    }
}
