/*----------------------------------------------------------------------------------------------
 * Copyright 2014 Federal IT Steering Unit FITSU Licensed under the Apache License, Version 2.0 (the "License"); you
 * may not use this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0 Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 *---------------------------------------------------------------------------------------------*/
package ch.admin.isb.hermes5.tools.deploysupport;

public class Main {

    public static void main(String[] args) {
        if(args.length != 7){
            System.out.println("Usage: {open|close} <rdsSecurityGroupName> <ec2securityGroupName> <secretKey> <accessKeyId> <clientIp> <targetPort>");
            return;
        }
        String rdsRegionEndpoint = "rds.eu-west-1.amazonaws.com";
        String ec2RegionEndpoint = "ec2.eu-west-1.amazonaws.com";
        String action = args[0];
        String rdsSecurityGroupName = args[1];
        String ec2securityGroupName = args[2];

        String secretKey = args[3];
        String accessKeyId = args[4];
        String clientIp = args[5];
        Integer targetPort = Integer.valueOf(args[6]);
        
        DeploySupport deploySupport = new DeploySupport(rdsSecurityGroupName, rdsRegionEndpoint, ec2securityGroupName,
                ec2RegionEndpoint, secretKey, accessKeyId, clientIp, targetPort);
        if(action.equals("open")){
            deploySupport.addIpToEC2SecurityGroup();
            deploySupport.addMyIPToRDSSecurityGroup();
        }
        if(action.equals("close")){
            deploySupport.removePortFromEC2SecurityGroup();
            deploySupport.removeMyIPFromRDSSecurityGroup();
        }
    }

}
