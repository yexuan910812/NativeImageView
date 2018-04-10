package com.github.henryye.nativeiv.util;

import com.github.henryye.nativeiv.delegate.Log;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FilterInputStream;
import java.io.IOException;

public class FileSeekingInputStream extends FilterInputStream {
    private long mMarkPosition = 0;

    public FileSeekingInputStream(String path) throws FileNotFoundException {
        super (new FileInputStream(path));
    }

    public FileSeekingInputStream(FileInputStream in) {
        super(in);
    }

    @Override
    public boolean markSupported() {
        return true;
    }

    @Override
    public void mark(int readlimit) {
        try {
            mMarkPosition = ((FileInputStream) in).getChannel().position();
        } catch (Exception e) {
            Log.printErrStackTrace("MicroMsg.FileSeekingInputStream", e, "Failed seeking FileChannel.");
        }
    }

    @Override
    public void reset() throws IOException {
        ((FileInputStream) in).getChannel().position(mMarkPosition);
    }
}

