package com.betwowt.inviteRegister;


import io.izzel.taboolib.loader.Plugin;
import io.izzel.taboolib.module.config.TConfig;
import io.izzel.taboolib.module.dependency.Dependency;
import io.izzel.taboolib.module.inject.TInject;
import io.izzel.taboolib.module.locale.TLocale;


@Dependency(maven = "io.grpc:grpc-netty-shaded:1.35.0")
@Dependency(maven = "io.grpc:grpc-protobuf:1.35.0")
@Dependency(maven = "io.grpc:grpc-stub:1.35.0")
@Dependency(maven = "io.grpc:grpc-api:1.35.0")
@Dependency(maven = "io.grpc:grpc-context:1.35.0")
@Dependency(maven = "io.grpc:grpc-core:1.35.0")
@Dependency(maven = "io.grpc:grpc-protobuf-lite:1.35.0")
@Dependency(maven = "com.google.api.grpc:proto-google-common-protos:2.0.1")
@Dependency(maven = "com.google.protobuf:protobuf-java:3.12.0")
@Dependency(maven = "io.perfmark:perfmark-api:0.23.0")
public class InviteRegisterPlugin extends Plugin {

    @TInject(value = "settings.yml",migrate = true)
    private static TConfig tConfig;

    @Override
    public void onLoad() {
        TLocale.sendToConsole("PLUGIN.LOADING");
    }
}
