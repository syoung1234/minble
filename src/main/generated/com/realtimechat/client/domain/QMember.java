package com.realtimechat.client.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QMember is a Querydsl query type for Member
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QMember extends EntityPathBase<Member> {

    private static final long serialVersionUID = 769569503L;

    public static final QMember member = new QMember("member1");

    public final QBaseTimeEntity _super = new QBaseTimeEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final StringPath email = createString("email");

    public final BooleanPath emailConfirmation = createBoolean("emailConfirmation");

    public final ComparablePath<java.util.UUID> id = createComparable("id", java.util.UUID.class);

    public final StringPath nickname = createString("nickname");

    public final BooleanPath notification = createBoolean("notification");

    public final StringPath password = createString("password");

    public final StringPath phone = createString("phone");

    public final StringPath profilePath = createString("profilePath");

    public final EnumPath<Role> role = createEnum("role", Role.class);

    public final StringPath social = createString("social");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public QMember(String variable) {
        super(Member.class, forVariable(variable));
    }

    public QMember(Path<? extends Member> path) {
        super(path.getType(), path.getMetadata());
    }

    public QMember(PathMetadata metadata) {
        super(Member.class, metadata);
    }

}

