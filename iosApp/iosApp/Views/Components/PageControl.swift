//
//  PageControl.swift
//  iosApp
//
//  Created by Proxima Centauri on 04/02/21.
//  Copyright © 2021 orgName. All rights reserved.
//

import SwiftUI
import Foundation
import UIKit

struct PageControl: UIViewRepresentable {
    
    @State var numberOfPages: Int
    @Binding var currentPageIndex: Int
    
    func makeUIView(context: Context) -> UIPageControl {
        let control = UIPageControl()
        
        control.numberOfPages = numberOfPages
        control.currentPageIndicatorTintColor = UIColor.darkGray
        control.pageIndicatorTintColor = UIColor.gray
        
        return control
    }
    
    func updateUIView(_ uiView: UIPageControl, context: Context) {
        uiView.currentPage = currentPageIndex
    }
    
}
