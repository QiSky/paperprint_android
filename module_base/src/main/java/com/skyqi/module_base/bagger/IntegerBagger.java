package com.skyqi.module_base.bagger;///

import android.os.Parcel;

import com.hannesdorfmann.parcelableplease.ParcelBagger;

/// * @ProjectName: paperprint
/// * @Author: qifanxin
/// * @CreateDate: 2022/3/1 6:31 下午
/// * @Description: 文件说明
///
public class IntegerBagger implements ParcelBagger<Integer> {

    @Override
    public void write(Integer value, Parcel out, int flags) {
        if (value == null) {
            out.writeInt(0);
        } else {
            out.writeInt(value.intValue());
        }
    }

    @Override
    public Integer read(Parcel in) {
        return new Integer(in.readInt());
    }
}
