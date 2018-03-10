package org.ltc.iltalk.protobuf;

import org.ltc.iltalk.core.IDbEntity;
import org.ltc.iltalk.core.ILanguageVM;


// Name of the message type.  Must be defined by one of the files in
//           // proto_files.
//           required string type_name = 2;
//           // The message data.
//           required bytes message_data = 3;
//           }
//
//           #  General Algorithm
//
//           Language lang1, lang2;
//
//           1) if lang1 == lang2 then error(); // do nothing or simple call??
//           2) peer1 = init( lang1 );
//           3) peer2 = init( lang2 );
//           4) if not peer1.isRegistered( lang1, lang2 ) &&
//           lang1.is_embeddable_into( lang2 ))
//
//           then peer1.register( call_mode, lang1, lang2 );
//           5) if not peer2.isRegistered( lang2, lang1 ) &&
//           lang2.is_embeddable_into( lang1 ))
//
//           then peer2.register( call_mode, lang2, lang1 );
//
//           //Starting call
//           6) lang1.call( lang2, call_mode, exec_mode, args, return, exception );
//
//
//
//
//           LangModel< Lang > Lang::call( Lang lang,
//           CallMode call_mode,
//           ExecMode exec-mode,
//           LangModel< Lang > args,
//           LangModel< Lang > return,
//           LangModel< Lang > expt)
//           {
//           Proto proto = lang.getProto();
//           //not existing or not compiled or had been modified.
//           if( proto == null ) {
//           proto = lang.generateProto();
//           }
//
//           bidiMap <LangModel<Lang>, Proto>  mapping = lang.compile_proto( proto );
//           }
//
//           LangModel< Lang > Lang::getModel( Lang lang, LangModel< Lang > args )
//           {
//
//           Map< LangModel1, Proto > mappingLang1_proto12 =
//           Map< LangModel2, Proto > mappingLang2_proto21 =
//
//           example:
//           "java" -> proto_java->prolog
//
////-------------------------------------------------------------------------------
//           Proto Peer::register( Lang lang1, Lang lang2 )
//           `    {
//
//           P--roto proto12 = generateProto( lang1, lang2 );
//           lang1.embed( lang2 );
////        Proto proto21 = generateProto( lang2, lang1 );
//
//           return proto12;
//           }
//
////-------------------------------------------------------------------------------
//
////==============================================================================
//class LangModel {
//    Lang model/ Data model/ Term model  is the Property list.
//
//    Property
//}
//
//class Property
//{
//
//    string   name;
//    object   value;
//}
//
////================================================================================================================
//Model: **relational\propiertary **
//
//        id
//        pname ( topmost property name ( entity )),
//        ( property values ( known: prolog[:term], logtalk[:term], proto[:def], java[:def],
//        prolog:term:atomic:atom          ...))
//        '$prop$'( ID,   entity, term ), // entity can be the term as well as its descendants (rudimentary type system)
//        '$prop$'( ID+1  compound, a ),
//        '$prop$'( ID+1, list, ID+2 ),
//        '$prop$'( ID+2, var,  X ),
//        '$prop$'( ID+3, compound, b ),
//
//
//
//
//        proto lang model:
//        ==================
//
//        prop_list = ['$prop$'( ID,      compound, proto:message ),
//        '$prop$'( ID,      list, ID+1 ),
//
//        '$prop$'( ID+1,    compound, proto:field ),
//        '$prop$'( ID+2,    list, ID+4 ),
//        '$prop$'( ID+3,    integer, 1 ),
//
//        '$prop$'( ID+4,    compound, proto:field ),
//        '$prop$'( ID+5,    list, ID+6),
//        '$prop$'( ID+6,    <>, <>),
//        ]
//


/**
 *
 */
public interface ILanguageModel extends IDbEntity {

    /**
     *
     */
    void loadNewLanguage();

    /**
     * @param lang
     * @return
     */
    ILanguageVM getVirtualMachine(ILanguageModel lang);

}