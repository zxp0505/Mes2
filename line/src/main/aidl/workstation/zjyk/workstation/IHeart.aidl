// IHeart.aidl
package workstation.zjyk.workstation;

// Declare any non-default types here with import statements
import workstation.zjyk.workstation.HeartBean;
interface IHeart {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
    void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat,
            double aDouble, String aString);

            void updateHeartBean(in  HeartBean heartBean);
}
