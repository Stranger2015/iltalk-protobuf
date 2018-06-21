package org.ltc.iltalk.core;

import javax.lang.model.element.Name;

/**
 *
 *      MongoDB. Open-source document database.
 CouchDB. Database that uses JSON for documents, JavaScript for MapReduce queries, and regular HTTP for an API.
 GemFire. ...
 Redis. ...
 Cassandra. ...
 memcached. ..b
 Hazelcast. ...
 HBase.
 */
public interface INameable{

    /**
     * @return
     */
   Name getName();

    /**
     * @param name
     * @return
     */
   Name setName( Name name );
}
