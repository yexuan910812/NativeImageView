//
// Created by henryye on 2018/4/10.
//

#ifndef NATIVEIMAGEVIEW_BITMAP_HOLDER_H
#define NATIVEIMAGEVIEW_BITMAP_HOLDER_H

#include <vector>


class bitmap_holder {
public:
    long decode_data(char* origin_data);
    void recycle(long ptr);
private:
    struct native_bm_data {
        char* bitmap_ptr;
        int config_type;
    };
    std::vector<native_bm_data> held_bitmaps;
};


#endif //NATIVEIMAGEVIEW_BITMAP_HOLDER_H
