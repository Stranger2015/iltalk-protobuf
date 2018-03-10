/*
 * ProfaneDB - A Protocol Buffers database.
 * Copyright (C) 2017  "Giorgio Azzinnaro" <giorgio.azzinnaro@gmail.com>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 */

#ifndef PROFANEDB_STORAGE_SERVER_H
#define PROFANEDB_STORAGE_SERVER_H

#include <profanedb/protobuf/db.pb.h>
#include <profanedb/protobuf/db.grpc.pb.h>

#include <grpc++/grpc++.h>
#include <grpc/support/log.h>

#include <boost/di.hpp>
#include <boost/log/core.hpp>
#include <boost/log/trivial.hpp>
#include <boost/log/expressions.hpp>

#include <profanedb/format/protobuf/marshaller.h>
#include <profanedb/vault/rocksdb/storage.h>

#include <profanedb/db.hpp>

#include "config.h"

namespace profanedb {
namespace server {

// This is a layer to use ProfaneDB with gRPC and Protobuf objects
class Server
{
public:
    Server(const profanedb::server::Config & config);
    ~Server();
    
    void Run();
    
private:
    void HandleRpcs();
    
    std::unique_ptr<grpc::Server> server;
    
    class DbServiceImpl : public profanedb::protobuf::Db::Service {
    public:
        DbServiceImpl(
            std::shared_ptr<profanedb::vault::rocksdb::Storage> storage,
            std::shared_ptr<profanedb::format::protobuf::Marshaller> marshaller,
            std::shared_ptr<profanedb::format::protobuf::Loader> loader);
        
        grpc::Status Get(
            grpc::ServerContext * context,
            const profanedb::protobuf::GetReq * request,
            profanedb::protobuf::GetResp* response) override;
        
        grpc::Status Put(
            grpc::ServerContext * context,
            const profanedb::protobuf::PutReq * request,
            profanedb::protobuf::PutResp * response) override;
        
        grpc::Status Delete(
            grpc::ServerContext * context,
            const profanedb::protobuf::DelReq * request,
            profanedb::protobuf::DelResp * response) override;
        
    private:
        std::shared_ptr<profanedb::vault::rocksdb::Storage> rocksdbStorage;
        std::shared_ptr<profanedb::format::protobuf::Marshaller> protobufMarshaller;
        
        std::unique_ptr< profanedb::Db<google::protobuf::Message> > profane;
        
        std::shared_ptr<profanedb::format::protobuf::Loader> loader;
    };
    std::unique_ptr<DbServiceImpl> service;
    
    const profanedb::server::Config & config;
};

}
}

#endif // PROFANEDB_STORAGE_SERVER_H
