//
// Created by henryye on 2018/4/10.
//

#ifndef NATIVEIMAGEVIEW_BITMAP_HOLDER_H
#define NATIVEIMAGEVIEW_BITMAP_HOLDER_H

#include <vector>


class bitmap_holder {
public:
    long decode_png(char* png_data);
    long decode_jpeg(char* jpeg_data);
    void recycle(long ptr);
    bool get_format(char* origin_data);
private:
    struct native_bm_data {
        char* bitmap_ptr;
        int config_type;
    };
    std::vector<native_bm_data> held_bitmaps;
};


#endif //NATIVEIMAGEVIEW_BITMAP_HOLDER_H
