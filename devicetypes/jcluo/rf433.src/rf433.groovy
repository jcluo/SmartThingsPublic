/**
 *  RF433
 *
 *  Copyright 2016 Jiancong Luo
 *
 *  Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 *  in compliance with the License. You may obtain a copy of the License at:
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software distributed under the License is distributed
 *  on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License
 *  for the specific language governing permissions and limitations under the License.
 *
 */
preferences {
  input ("ip", "text", title: "controller IP", 
    description: "RF433 controller IP address.", default: "192.168.2.105",
    required: true)
  input ("id", "enum", title: "switch id", 
    description: "RF433 switch id", options: ["1","2","3","4","5"],
    required: true)
}

metadata {
	definition (name: "RF433", namespace: "jcluo", author: "Jiancong Luo") {
		capability "Switch"
	}

	simulator {
		// TODO: define status and reply messages here
	}

	tiles {
		standardTile("button", "device.switch", width: 2, height: 2, canChangeIcon: true) {
			state "off", label: 'Off', action: "switch.on", icon: "st.Kids.kid10", backgroundColor: "#ffffff", nextState: "on"
			state "on", label: 'On', action: "switch.off", icon: "st.Kids.kid10", backgroundColor: "#79b821", nextState: "off"
		}
	}
    main "button"
	details(["button"])
}

// parse events into attributes
def parse(String description) {
	log.debug "Parsing '${description}'"
}

// handle commands
def on() {
	log.debug "Executing 'on'"
   	def result = new physicalgraph.device.HubAction(
    	method: "POST",
    	path: "/rfoutlet/toggle.php",
    	headers: [HOST: "${settings.ip}:80",
        	      "Content-Type": "application/x-www-form-urlencoded"
                 ],
        body: """Id=${settings.id}&Status=ON"""
	)
    return result
}

def off() {
	log.debug "Executing 'off'"
   	def result = new physicalgraph.device.HubAction(
    	method: "POST",
    	path: "/rfoutlet/toggle.php",
    	headers: [HOST: "${settings.ip}:80",
        	      "Content-Type": "application/x-www-form-urlencoded"
                 ],
        body: """Id=${settings.id}&Status=OFF"""
	)
    return result
}