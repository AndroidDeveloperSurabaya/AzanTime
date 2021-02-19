//
//  AdzanListTab.swift
//  iosApp
//
//  Created by Proxima Centauri on 27/01/21.
//  Copyright © 2021 orgName. All rights reserved.
//

import SwiftUI
import shared
import Foundation

struct PrayerListTab: View {
    
    @ObservedObject var viewModel: PrayerTimeViewModel = PrayerTimeViewModel()
    @ObservedObject var notificationManager = NotificationManager()
    @State var currentPageIndex = 0
    
    @State private var isLoading: Bool = true
    @State private var rotation: Bool = false
    
    @State var subViews: Array<Dictionary<String, String>> = []
    @State var timedate: String = ""
    
    var body: some View {
        VStack {
            if (isLoading) {
                Circle()
                    .trim(from: 0, to: 0.7)
                    .stroke(Color("ColorAccent"), lineWidth: 5)
                    .frame(width: 100, height: 100)
                    .rotationEffect(Angle(degrees: rotation ? 360 : 0))
                    .animation(Animation.linear(duration: 0.75).repeatForever(autoreverses: false))
                    .onAppear() {
                        self.rotation.toggle()
                    }
            } else {
                HStack {
                    Image(systemName: "location.fill")
                        .foregroundColor(Color("ColorAccent"))
                    Text("Surabaya, Indonesia")
                        .font(.footnote)
                        .foregroundColor(Color("ColorAccent"))
                }
                Spacer()
                HStack {
                    Image("maghrib")
                        .resizable()
                        .frame(width: 100, height: 100, alignment: .center)
                        .padding(.horizontal)
                    
                    VStack {
                        Text(self.viewModel.timeToNextPray?.uppercased() ?? "maghrib".uppercased())
                            .font(.largeTitle)
                            .foregroundColor(Color("ColorAccent"))
                            .bold()
                            .padding(.vertical, 4)
                        
                        Text("3 Hours 14 minutes remaining")
                            .font(.body).foregroundColor(Color("ColorAccent"))
                            .multilineTextAlignment(.center)
                    }
                    .padding(.horizontal)
                }
            
                Spacer()
                
                PraySubView(prays: subViews, timedate: "Februari 17 2021")
                
                PageControl(numberOfPages: 1, currentPageIndex: $currentPageIndex)
                
                Spacer()
            }
        }.onAppear {
            self.viewModel.getData() { result in
                if (result == 200) {
                    let pray = self.viewModel.data?.data?.timings
                    var checked = false
                    
                    let currentDate = Date()
                    let formatter = DateFormatter()
                    formatter.timeZone = .current
                    formatter.dateFormat = "HH:mm"
                    let currentTime = formatter.string(from: currentDate).split(separator: ":")
                    let current = Calendar.current.date(bySettingHour: Int(currentTime[0]) ?? 0, minute: Int(currentTime[1]) ?? 0, second: 0, of: Date()) ?? Date()
                    
                    let times = ["fajr", "sunrise", "dhuhr", "asr", "maghrib", "isha"]
                    
                    for time in times {
                        let timeParse = pray?.value(forKey: time) as! String
                        let timeSplit = timeParse.split(separator: ":")

                        self.subViews.append(["name": time , "time": timeParse])
                        
                        if !checked {
                            let dateCheck = Calendar.current.date(bySettingHour: Int(timeSplit[0]) ?? 0, minute: Int(timeSplit[1]) ?? 0, second: 0, of: Date()) ?? Date()
                            
                            if dateCheck >= current {
                                let timeToLaunch = dateCheck.currentTimeMillis() - current.currentTimeMillis()
                                self.viewModel.timeToNextPray = time

                                DispatchQueue.global().async {
                                    self.notificationManager.sendNotification(title: "Time To Pray \(time.uppercased())", subTitle: nil, body: "Gas", launchIn: Double(timeToLaunch))
                                }
                                checked = true
                            }
                        }

                    }
                    
                    self.isLoading.toggle()
                }
            }
        }
    }
}

struct PraySubView: View {
    
    @State var prays: Array<Dictionary<String, String>>
    @State var timedate: String
    
    var body: some View {
        VStack {
            Text(timedate)
                .font(.callout)
                .foregroundColor(Color("ColorAccent"))
            
            List {
                ForEach(prays, id: \.self) { pray in
                    PrayItemList(pray: pray)
                }
            }
        }.padding()
        .frame(width: UIScreen.main.bounds.width, height: UIScreen.main.bounds.height / 2.5, alignment: /*@START_MENU_TOKEN@*/.center/*@END_MENU_TOKEN@*/)
    }
}

struct PrayItemList: View {
    
    @State var pray: Dictionary<String, String>
    
    var body: some View {
        HStack {
            Image(pray["name"]?.lowercased() ?? "sunrise")
                .resizable()
                .frame(width: 25, height: 25, alignment: .center)
                .padding(.horizontal, 4)
            Text(pray["name"]?.uppercased() ?? "SUNRISE")
                .font(.system(size: 18))
                .foregroundColor(Color("ColorAccent"))
            Spacer()
            Text(pray["time"] ?? "03:12").font(.system(size: 18))
                .foregroundColor(Color("ColorAccent"))
            Image("volume_on")
        }
    }
    
}

struct PrayerListTab_Previews: PreviewProvider {
    static var previews: some View {
        PrayerListTab()
    }
}
