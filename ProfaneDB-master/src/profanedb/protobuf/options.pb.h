// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: profanedb/protobuf/options.proto

#ifndef PROTOBUF_profanedb_2fprotobuf_2foptions_2eproto__INCLUDED
#define PROTOBUF_profanedb_2fprotobuf_2foptions_2eproto__INCLUDED

#include <string>

#include <google/protobuf/stubs/common.h>

#if GOOGLE_PROTOBUF_VERSION < 3003000
#error This file was generated by a newer version of protoc which is
#error incompatible with your Protocol Buffer headers.  Please update
#error your headers.
#endif
#if 3003000 < GOOGLE_PROTOBUF_MIN_PROTOC_VERSION
#error This file was generated by an older version of protoc which is
#error incompatible with your Protocol Buffer headers.  Please
#error regenerate this file with a newer version of protoc.
#endif

#include <google/protobuf/io/coded_stream.h>
#include <google/protobuf/arena.h>
#include <google/protobuf/arenastring.h>
#include <google/protobuf/generated_message_table_driven.h>
#include <google/protobuf/generated_message_util.h>
#include <google/protobuf/metadata.h>
#include <google/protobuf/message.h>
#include <google/protobuf/repeated_field.h>  // IWYU pragma: export
#include <google/protobuf/extension_set.h>  // IWYU pragma: export
#include <google/protobuf/unknown_field_set.h>
#include <google/protobuf/descriptor.pb.h>
// @@protoc_insertion_point(includes)
namespace google {
namespace protobuf {
class DescriptorProto;
class DescriptorProtoDefaultTypeInternal;
extern DescriptorProtoDefaultTypeInternal _DescriptorProto_default_instance_;
class DescriptorProto_ExtensionRange;
class DescriptorProto_ExtensionRangeDefaultTypeInternal;
extern DescriptorProto_ExtensionRangeDefaultTypeInternal _DescriptorProto_ExtensionRange_default_instance_;
class DescriptorProto_ReservedRange;
class DescriptorProto_ReservedRangeDefaultTypeInternal;
extern DescriptorProto_ReservedRangeDefaultTypeInternal _DescriptorProto_ReservedRange_default_instance_;
class EnumDescriptorProto;
class EnumDescriptorProtoDefaultTypeInternal;
extern EnumDescriptorProtoDefaultTypeInternal _EnumDescriptorProto_default_instance_;
class EnumOptions;
class EnumOptionsDefaultTypeInternal;
extern EnumOptionsDefaultTypeInternal _EnumOptions_default_instance_;
class EnumValueDescriptorProto;
class EnumValueDescriptorProtoDefaultTypeInternal;
extern EnumValueDescriptorProtoDefaultTypeInternal _EnumValueDescriptorProto_default_instance_;
class EnumValueOptions;
class EnumValueOptionsDefaultTypeInternal;
extern EnumValueOptionsDefaultTypeInternal _EnumValueOptions_default_instance_;
class FieldDescriptorProto;
class FieldDescriptorProtoDefaultTypeInternal;
extern FieldDescriptorProtoDefaultTypeInternal _FieldDescriptorProto_default_instance_;
class FieldOptions;
class FieldOptionsDefaultTypeInternal;
extern FieldOptionsDefaultTypeInternal _FieldOptions_default_instance_;
class FileDescriptorProto;
class FileDescriptorProtoDefaultTypeInternal;
extern FileDescriptorProtoDefaultTypeInternal _FileDescriptorProto_default_instance_;
class FileDescriptorSet;
class FileDescriptorSetDefaultTypeInternal;
extern FileDescriptorSetDefaultTypeInternal _FileDescriptorSet_default_instance_;
class FileOptions;
class FileOptionsDefaultTypeInternal;
extern FileOptionsDefaultTypeInternal _FileOptions_default_instance_;
class GeneratedCodeInfo;
class GeneratedCodeInfoDefaultTypeInternal;
extern GeneratedCodeInfoDefaultTypeInternal _GeneratedCodeInfo_default_instance_;
class GeneratedCodeInfo_Annotation;
class GeneratedCodeInfo_AnnotationDefaultTypeInternal;
extern GeneratedCodeInfo_AnnotationDefaultTypeInternal _GeneratedCodeInfo_Annotation_default_instance_;
class MessageOptions;
class MessageOptionsDefaultTypeInternal;
extern MessageOptionsDefaultTypeInternal _MessageOptions_default_instance_;
class MethodDescriptorProto;
class MethodDescriptorProtoDefaultTypeInternal;
extern MethodDescriptorProtoDefaultTypeInternal _MethodDescriptorProto_default_instance_;
class MethodOptions;
class MethodOptionsDefaultTypeInternal;
extern MethodOptionsDefaultTypeInternal _MethodOptions_default_instance_;
class OneofDescriptorProto;
class OneofDescriptorProtoDefaultTypeInternal;
extern OneofDescriptorProtoDefaultTypeInternal _OneofDescriptorProto_default_instance_;
class OneofOptions;
class OneofOptionsDefaultTypeInternal;
extern OneofOptionsDefaultTypeInternal _OneofOptions_default_instance_;
class ServiceDescriptorProto;
class ServiceDescriptorProtoDefaultTypeInternal;
extern ServiceDescriptorProtoDefaultTypeInternal _ServiceDescriptorProto_default_instance_;
class ServiceOptions;
class ServiceOptionsDefaultTypeInternal;
extern ServiceOptionsDefaultTypeInternal _ServiceOptions_default_instance_;
class SourceCodeInfo;
class SourceCodeInfoDefaultTypeInternal;
extern SourceCodeInfoDefaultTypeInternal _SourceCodeInfo_default_instance_;
class SourceCodeInfo_Location;
class SourceCodeInfo_LocationDefaultTypeInternal;
extern SourceCodeInfo_LocationDefaultTypeInternal _SourceCodeInfo_Location_default_instance_;
class UninterpretedOption;
class UninterpretedOptionDefaultTypeInternal;
extern UninterpretedOptionDefaultTypeInternal _UninterpretedOption_default_instance_;
class UninterpretedOption_NamePart;
class UninterpretedOption_NamePartDefaultTypeInternal;
extern UninterpretedOption_NamePartDefaultTypeInternal _UninterpretedOption_NamePart_default_instance_;
}  // namespace protobuf
}  // namespace google
namespace profanedb {
namespace protobuf {
class FieldOptions;
class FieldOptionsDefaultTypeInternal;
extern FieldOptionsDefaultTypeInternal _FieldOptions_default_instance_;
}  // namespace protobuf
}  // namespace profanedb

namespace profanedb {
namespace protobuf {

namespace protobuf_profanedb_2fprotobuf_2foptions_2eproto {
// Internal implementation detail -- do not call these.
struct TableStruct {
  static const ::google::protobuf::internal::ParseTableField entries[];
  static const ::google::protobuf::internal::AuxillaryParseTableField aux[];
  static const ::google::protobuf::internal::ParseTable schema[];
  static const ::google::protobuf::uint32 offsets[];
  static void InitDefaultsImpl();
  static void Shutdown();
};
void AddDescriptors();
void InitDefaults();
}  // namespace protobuf_profanedb_2fprotobuf_2foptions_2eproto

// ===================================================================

class FieldOptions : public ::google::protobuf::Message /* @@protoc_insertion_point(class_definition:profanedb.protobuf.FieldOptions) */ {
 public:
  FieldOptions();
  virtual ~FieldOptions();

  FieldOptions(const FieldOptions& from);

  inline FieldOptions& operator=(const FieldOptions& from) {
    CopyFrom(from);
    return *this;
  }

  inline const ::google::protobuf::UnknownFieldSet& unknown_fields() const {
    return _internal_metadata_.unknown_fields();
  }

  inline ::google::protobuf::UnknownFieldSet* mutable_unknown_fields() {
    return _internal_metadata_.mutable_unknown_fields();
  }

  static const ::google::protobuf::Descriptor* descriptor();
  static const FieldOptions& default_instance();

  static inline const FieldOptions* internal_default_instance() {
    return reinterpret_cast<const FieldOptions*>(
               &_FieldOptions_default_instance_);
  }
  static PROTOBUF_CONSTEXPR int const kIndexInFileMessages =
    0;

  void Swap(FieldOptions* other);

  // implements Message ----------------------------------------------

  inline FieldOptions* New() const PROTOBUF_FINAL { return New(NULL); }

  FieldOptions* New(::google::protobuf::Arena* arena) const PROTOBUF_FINAL;
  void CopyFrom(const ::google::protobuf::Message& from) PROTOBUF_FINAL;
  void MergeFrom(const ::google::protobuf::Message& from) PROTOBUF_FINAL;
  void CopyFrom(const FieldOptions& from);
  void MergeFrom(const FieldOptions& from);
  void Clear() PROTOBUF_FINAL;
  bool IsInitialized() const PROTOBUF_FINAL;

  size_t ByteSizeLong() const PROTOBUF_FINAL;
  bool MergePartialFromCodedStream(
      ::google::protobuf::io::CodedInputStream* input) PROTOBUF_FINAL;
  void SerializeWithCachedSizes(
      ::google::protobuf::io::CodedOutputStream* output) const PROTOBUF_FINAL;
  ::google::protobuf::uint8* InternalSerializeWithCachedSizesToArray(
      bool deterministic, ::google::protobuf::uint8* target) const PROTOBUF_FINAL;
  int GetCachedSize() const PROTOBUF_FINAL { return _cached_size_; }
  private:
  void SharedCtor();
  void SharedDtor();
  void SetCachedSize(int size) const PROTOBUF_FINAL;
  void InternalSwap(FieldOptions* other);
  private:
  inline ::google::protobuf::Arena* GetArenaNoVirtual() const {
    return NULL;
  }
  inline void* MaybeArenaPtr() const {
    return NULL;
  }
  public:

  ::google::protobuf::Metadata GetMetadata() const PROTOBUF_FINAL;

  // nested types ----------------------------------------------------

  // accessors -------------------------------------------------------

  // optional bool key = 1;
  bool has_key() const;
  void clear_key();
  static const int kKeyFieldNumber = 1;
  bool key() const;
  void set_key(bool value);

  // @@protoc_insertion_point(class_scope:profanedb.protobuf.FieldOptions)
 private:
  void set_has_key();
  void clear_has_key();

  ::google::protobuf::internal::InternalMetadataWithArena _internal_metadata_;
  ::google::protobuf::internal::HasBits<1> _has_bits_;
  mutable int _cached_size_;
  bool key_;
  friend struct protobuf_profanedb_2fprotobuf_2foptions_2eproto::TableStruct;
};
// ===================================================================

static const int kOptionsFieldNumber = 1036;
extern ::google::protobuf::internal::ExtensionIdentifier< ::google::protobuf::FieldOptions,
    ::google::protobuf::internal::MessageTypeTraits< ::profanedb::protobuf::FieldOptions >, 11, false >
  options;

// ===================================================================

#if !PROTOBUF_INLINE_NOT_IN_HEADERS
// FieldOptions

// optional bool key = 1;
inline bool FieldOptions::has_key() const {
  return (_has_bits_[0] & 0x00000001u) != 0;
}
inline void FieldOptions::set_has_key() {
  _has_bits_[0] |= 0x00000001u;
}
inline void FieldOptions::clear_has_key() {
  _has_bits_[0] &= ~0x00000001u;
}
inline void FieldOptions::clear_key() {
  key_ = false;
  clear_has_key();
}
inline bool FieldOptions::key() const {
  // @@protoc_insertion_point(field_get:profanedb.protobuf.FieldOptions.key)
  return key_;
}
inline void FieldOptions::set_key(bool value) {
  set_has_key();
  key_ = value;
  // @@protoc_insertion_point(field_set:profanedb.protobuf.FieldOptions.key)
}

#endif  // !PROTOBUF_INLINE_NOT_IN_HEADERS

// @@protoc_insertion_point(namespace_scope)


}  // namespace protobuf
}  // namespace profanedb

// @@protoc_insertion_point(global_scope)

#endif  // PROTOBUF_profanedb_2fprotobuf_2foptions_2eproto__INCLUDED