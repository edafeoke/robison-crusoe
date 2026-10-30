/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.rms.RecordStore
 *  javax.microedition.rms.RecordStoreException
 */
package data;

import data.b;
import data.k;
import data.z;
import javax.microedition.rms.RecordStore;
import javax.microedition.rms.RecordStoreException;

public final class e {
    private static e a = null;

    private e() {
    }

    public static final e a() {
        if (k.a()) {
            if (a == null) {
                a = new e();
            }
            return a;
        }
        throw new b("Unauthorised operation");
    }

    public final void a(String string, int n2, int n3, byte[] byArray) throws z {
        RecordStore recordStore = null;
        ++n2;
        try {
            recordStore = RecordStore.openRecordStore((String)string, (boolean)true);
            int n4 = recordStore.getNumRecords();
            while (n4 < n2) {
                recordStore.addRecord(null, 0, 0);
                ++n4;
            }
            int n5 = recordStore.getSizeAvailable() + recordStore.getRecordSize(n2);
            if (n5 < n3) {
                recordStore.closeRecordStore();
                throw new z("Not enought space");
            }
            recordStore.setRecord(n2, byArray, 0, n3);
            if (recordStore != null) {
                recordStore.closeRecordStore();
            }
            return;
        }
        catch (RecordStoreException recordStoreException) {
            throw new z("CelsiusRecordStoreManager: " + recordStoreException.getMessage());
        }
    }

    public final byte[] a(String string, int n2) throws z {
        byte[] byArray;
        RecordStore recordStore = null;
        ++n2;
        try {
            recordStore = RecordStore.openRecordStore((String)string, (boolean)false);
            int n3 = recordStore.getNumRecords();
            if (n3 < n2) {
                return null;
            }
            byArray = recordStore.getRecord(n2);
            if (recordStore != null) {
                recordStore.closeRecordStore();
            }
        }
        catch (RecordStoreException recordStoreException) {
            throw new z("CelsiusRecordStoreManager: " + recordStoreException.getMessage());
        }
        return byArray;
    }
}

