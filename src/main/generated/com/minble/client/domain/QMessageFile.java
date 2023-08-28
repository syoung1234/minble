package com.minble.client.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QMessageFile is a Querydsl query type for MessageFile
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QMessageFile extends EntityPathBase<MessageFile> {

    private static final long serialVersionUID = 876897732L;

    public static final QMessageFile messageFile = new QMessageFile("messageFile");

    public final QBaseTimeEntity _super = new QBaseTimeEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final StringPath filename = createString("filename");

    public final StringPath filePath = createString("filePath");

    public final NumberPath<Long> fileSize = createNumber("fileSize", Long.class);

    public final StringPath fileType = createString("fileType");

    public final NumberPath<Integer> id = createNumber("id", Integer.class);

    public final StringPath originalFileName = createString("originalFileName");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public QMessageFile(String variable) {
        super(MessageFile.class, forVariable(variable));
    }

    public QMessageFile(Path<? extends MessageFile> path) {
        super(path.getType(), path.getMetadata());
    }

    public QMessageFile(PathMetadata metadata) {
        super(MessageFile.class, metadata);
    }

}

