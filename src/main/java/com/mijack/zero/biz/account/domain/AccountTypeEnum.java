///*
// *    Copyright 2019 Mi&Jack
// *
// *    Licensed under the Apache License, Version 2.0 (the "License");
// *    you may not use this file except in compliance with the License.
// *    You may obtain a copy of the License at
// *
// *        http://www.apache.org/licenses/LICENSE-2.0
// *
// *    Unless required by applicable law or agreed to in writing, software
// *    distributed under the License is distributed on an "AS IS" BASIS,
// *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// *    See the License for the specific language governing permissions and
// *    limitations under the License.
// */
//
//package com.mijack.zero.biz.account.domain;
//
//import lombok.Getter;
//
///**
// * @author Mi&Jack
// */
//public enum AccountTypeEnum implements AccountType {
//    /**
//     * 支付宝
//     */
//    AliPay(0,"支付宝"),
//    /**
//     * 微信支付
//     */
//    WeixinPay(1,"微信支付"),
//    /**
//     * 银行
//     */
//    BANK(2,"银行");
//    @Getter
//    private int id;
//    @Getter
//    private String name;
//
//    AccountTypeEnum(int id, String name) {
//        this.id = id;
//        this.name = name;
//    }
//
//    public static AccountType of(Long accountTypeCode) {
//        for (AccountType accountTypeEnum : values()) {
//            if (accountTypeEnum.getId() == accountTypeCode) {
//                return accountTypeEnum;
//            }
//        }
//        return null;
//    }
//
//}
