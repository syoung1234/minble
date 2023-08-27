package com.minble.client.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QEmailToken is a Querydsl query type for EmailToken
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QEmailToken extends EntityPathBase<EmailToken> {

    private static final long serialVersionUID = -574812164L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QEmailToken emailToken = new QEmailToken("emailToken");

    public final DateTimePath<java.time.LocalDateTime> expirationDate = createDateTime("expirationDate", java.time.LocalDateTime.class);

    public final BooleanPath expired = createBoolean("expired");

    public final StringPath id = createString("id");

    public final QMember member;

    public QEmailToken(String variable) {
        this(EmailToken.class, forVariable(variable), INITS);
    }

    public QEmailToken(Path<? extends EmailToken> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QEmailToken(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QEmailToken(PathMetadata metadata, PathInits inits) {
        this(EmailToken.class, metadata, inits);
    }

    public QEmailToken(Class<? extends EmailToken> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.member = inits.isInitialized("member") ? new QMember(forProperty("member")) : null;
    }

}

