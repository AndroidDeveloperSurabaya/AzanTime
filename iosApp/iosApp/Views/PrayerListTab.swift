//
//  AdzanListTab.swift
//  iosApp
//
//  Created by Proxima Centauri on 27/01/21.
//  Copyright © 2021 orgName. All rights reserved.
//

import SwiftUI

struct PrayerListTab: View {
    
    @ObservedObject var viewModel: PrayerTimeViewModel = PrayerTimeViewModel()
    let prays = [
        ["name": "Fajr", "time": "04:35"],
        ["name": "Dhuhr", "time": "11.45"],
        ["name": "Ashr", "time": "03.12"],
        ["name": "Maghrib", "time": "17.50"],
        ["name": "Isya", "time": "19.14"],
    ]
    
    var body: some View {
        VStack {
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
                    Text("Maghrib".uppercased())
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
            
            VStack {
                Text("February 4 2021, 27 Rabiul Awal 1442")
                    .font(.callout)
                    .foregroundColor(Color("ColorAccent"))
                
                List {
                    ForEach(prays, id: \.self) { pray in
                        PrayItemList(pray: pray)
                    }
                }.padding()
                .frame(width: UIScreen.main.bounds.width, height: UIScreen.main.bounds.height / 3, alignment: /*@START_MENU_TOKEN@*/.center/*@END_MENU_TOKEN@*/)
            }
            
            Spacer()
        }.onAppear {
            self.viewModel.getData()
        }
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
            Text(pray["name"] ?? "SUNRISE").font(.system(size: 18))
                .foregroundColor(Color("ColorAccent"))
            Spacer()
            Text("03:12").font(.system(size: 18))
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
