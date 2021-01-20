package com.betwowt.inviteRegister.grpc;

import com.betwowt.grpc.*;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

public class InviteService {

    private final InviteServiceGrpc.InviteServiceBlockingStub blockingStub;

    private final ManagedChannel managedChannel;

    public InviteService(String host, int port) {
        managedChannel = ManagedChannelBuilder.forAddress(host, port)
                .usePlaintext()
                .build();
        blockingStub = InviteServiceGrpc.newBlockingStub(managedChannel);
    }

    public ShowRes show(String userId){
        ShowReq req = ShowReq.newBuilder()
                .setUserId(userId)
                .build();
        return blockingStub.show(req);
    }

    public InviteRes invite(String inviteUserId,String email){
        InviteReq req = InviteReq.newBuilder()
                .setInviteUserId(inviteUserId)
                .setInviteEmail(email)
                .build();
        return blockingStub.invite(req);
    }

    public void shutdown(){
        if (managedChannel!=null){
            managedChannel.shutdown();
        }
    }

}
