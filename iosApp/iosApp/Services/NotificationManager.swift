//
//  NotificationManager.swift
//  iosApp
//
//  Created by Proxima Centauri on 16/02/21.
//  Copyright © 2021 orgName. All rights reserved.
//

import Foundation
import SwiftUI

class NotificationManager: ObservableObject {
    
    var notifications = [Notification]()
    
    init() {
        UNUserNotificationCenter.current().requestAuthorization(options: [.alert, .badge, .sound]) { granted, error in
            if granted == true && error == nil {
                print("Notifications permitted")
            } else {
                print("Notifications not permitted")
            }
        }
    }
    
    func sendNotification(title: String, subTitle: String?, body: String, launchIn: Double) {
        let content = UNMutableNotificationContent()
        content.title = title
        if let subTitle = subTitle {
            content.subtitle = subTitle
        }
        content.body = body
        content.sound = UNNotificationSound.default
        
        let trigger = UNTimeIntervalNotificationTrigger(timeInterval: launchIn, repeats: false)
        let request = UNNotificationRequest(identifier: UUID().uuidString, content: content, trigger: trigger)
        
        UNUserNotificationCenter.current().add(request)
    }
    
}
