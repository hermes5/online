/*----------------------------------------------------------------------------------------------
 * Copyright 2014 Federal IT Steering Unit FITSU Licensed under the Apache License, Version 2.0 (the "License"); you
 * may not use this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0 Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 *---------------------------------------------------------------------------------------------*/
package ch.admin.isb.hermes5.tools.deploysupport;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.services.ec2.AmazonEC2;
import com.amazonaws.services.ec2.AmazonEC2Client;
import com.amazonaws.services.ec2.model.AuthorizeSecurityGroupIngressRequest;
import com.amazonaws.services.ec2.model.IpPermission;
import com.amazonaws.services.ec2.model.RevokeSecurityGroupIngressRequest;
import com.amazonaws.services.rds.AmazonRDS;
import com.amazonaws.services.rds.AmazonRDSClient;
import com.amazonaws.services.rds.model.AuthorizeDBSecurityGroupIngressRequest;
import com.amazonaws.services.rds.model.RevokeDBSecurityGroupIngressRequest;

public class DeploySupport {

    private final String rdsSecurityGroupName;
    private final String rdsRegionEndpoint;
    private final String secretKey;
    private final String accessKeyId;
    private final String clientIpRange;
    private final String ec2securityGroupName;
    private final String ec2RegionEndpoint;
    private final Integer targetPort;

    public DeploySupport(String rdsSecurityGroupName, String rdsRegionEndpoint, String ec2securityGroupName,
            String ec2RegionEndpoint, String secretKey, String accessKeyId, String clientIp, Integer targetPort) {
        this.rdsSecurityGroupName = rdsSecurityGroupName;
        this.rdsRegionEndpoint = rdsRegionEndpoint;
        this.ec2securityGroupName = ec2securityGroupName;
        this.ec2RegionEndpoint = ec2RegionEndpoint;
        this.secretKey = secretKey;
        this.accessKeyId = accessKeyId;
        this.clientIpRange = clientIp + "/32";
        this.targetPort = targetPort;
    }

    public void addIpToEC2SecurityGroup() {
        List<IpPermission> ipPermissions = new ArrayList<IpPermission>();
        System.out.println("adding tcp rule " + clientIpRange + " " + targetPort);
        ipPermissions.add(ec2TcpPermission());
        AuthorizeSecurityGroupIngressRequest authorizeSecurityGroupIngressRequest = new AuthorizeSecurityGroupIngressRequest(
                ec2securityGroupName, ipPermissions);
        try {
            ec2().authorizeSecurityGroupIngress(authorizeSecurityGroupIngressRequest);
            audit("Opened Port: " + targetPort);
        } catch (Exception e) {
            audit("Unable to opened port: " + targetPort);
            e.printStackTrace();
        }

    }

    private IpPermission ec2TcpPermission() {
        return new IpPermission().withIpRanges(clientIpRange).withFromPort(targetPort).withToPort(targetPort)
                .withIpProtocol("tcp");
    }

    public void removePortFromEC2SecurityGroup() {
        RevokeSecurityGroupIngressRequest revokeSecurityGroupIngressRequest = new RevokeSecurityGroupIngressRequest(
                ec2securityGroupName, Arrays.asList(ec2TcpPermission()));
        ec2().revokeSecurityGroupIngress(revokeSecurityGroupIngressRequest);
        audit("Closed Ports: " + targetPort);
    }

    private AmazonEC2 ec2() {
        AmazonEC2Client ec2 = new AmazonEC2Client(credentials());
        ec2.setEndpoint(ec2RegionEndpoint);
        return ec2;
    }

    public void addMyIPToRDSSecurityGroup() {

        AuthorizeDBSecurityGroupIngressRequest authorizeDBSecurityGroupIngressRequest = new AuthorizeDBSecurityGroupIngressRequest(
                rdsSecurityGroupName).withCIDRIP(clientIpRange);
        try {
            rds().authorizeDBSecurityGroupIngress(authorizeDBSecurityGroupIngressRequest);

            audit("Open for IP Range=" + clientIpRange);
        } catch (Exception e) {
            audit("Unable to opened for IP Range=" + clientIpRange);
            e.printStackTrace();
        }
    }

    public void removeMyIPFromRDSSecurityGroup() {

        RevokeDBSecurityGroupIngressRequest request = new RevokeDBSecurityGroupIngressRequest(rdsSecurityGroupName)
                .withCIDRIP(clientIpRange);
        rds().revokeDBSecurityGroupIngress(request);

        audit("No longer open for IP Range=" + clientIpRange);
    }

    private void audit(String s) {
        System.out.println("[AUDIT] " + s);
    }

    private AmazonRDS rds() {
        AmazonRDSClient rds = new AmazonRDSClient(credentials());
        rds.setEndpoint(rdsRegionEndpoint);
        return rds;
    }

    private AWSCredentials credentials() {
        return new AWSCredentials() {

            @Override
            public String getAWSSecretKey() {
                return secretKey;
            }

            @Override
            public String getAWSAccessKeyId() {
                return accessKeyId;
            }
        };
    }
}
