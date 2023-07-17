package com.realtimechat.client.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QSubscriber is a Querydsl query type for Subscriber
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QSubscriber extends EntityPathBase<Subscriber> {

    private static final long serialVersionUID = -287290835L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QSubscriber subscriber = new QSubscriber("subscriber");

    public final QBaseTimeEntity _super = new QBaseTimeEntity(this);

    public final QChatRoom chatRoom;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final StringPath customer_uid = createString("customer_uid");

    public final DateTimePath<java.time.LocalDateTime> expiredAt = createDateTime("expiredAt", java.time.LocalDateTime.class);

    public final NumberPath<Integer> id = createNumber("id", Integer.class);

    public final QMember member;

    public final BooleanPath status = createBoolean("status");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public QSubscriber(String variable) {
        this(Subscriber.class, forVariable(variable), INITS);
    }

    public QSubscriber(Path<? extends Subscriber> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QSubscriber(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QSubscriber(PathMetadata metadata, PathInits inits) {
        this(Subscriber.class, metadata, inits);
    }

    public QSubscriber(Class<? extends Subscriber> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.chatRoom = inits.isInitialized("chatRoom") ? new QChatRoom(forProperty("chatRoom"), inits.get("chatRoom")) : null;
        this.member = inits.isInitialized("member") ? new QMember(forProperty("member")) : null;
    }

}

