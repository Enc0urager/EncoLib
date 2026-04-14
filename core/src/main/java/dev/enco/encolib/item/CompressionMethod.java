package dev.enco.encolib.item;

import org.apache.commons.compress.compressors.bzip2.BZip2CompressorInputStream;
import org.apache.commons.compress.compressors.bzip2.BZip2CompressorOutputStream;
import org.apache.commons.compress.compressors.lz4.FramedLZ4CompressorInputStream;
import org.apache.commons.compress.compressors.lz4.FramedLZ4CompressorOutputStream;
import org.apache.commons.compress.compressors.lzma.LZMACompressorInputStream;
import org.apache.commons.compress.compressors.lzma.LZMACompressorOutputStream;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.DeflaterOutputStream;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;
import java.util.zip.InflaterInputStream;

public enum CompressionMethod {

    NONE {
        @Override
        byte[] compress(byte[] data) { return data; }
        @Override
        byte[] decompress(byte[] data) { return data; }
    },

    GZIP {
        @Override
        byte[] compress(byte[] data) throws IOException {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            try (GZIPOutputStream gzip = new GZIPOutputStream(out)) {
                gzip.write(data);
                gzip.finish();
            }
            return out.toByteArray();
        }

        @Override
        byte[] decompress(byte[] data) throws IOException {
            return readAll(new GZIPInputStream(new ByteArrayInputStream(data)));
        }
    },

    ZLIB {
        @Override
        public byte[] compress(byte[] data) throws IOException {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            try (DeflaterOutputStream def = new DeflaterOutputStream(out)) {
                def.write(data);
                def.finish();
            }
            return out.toByteArray();
        }

        @Override
        public byte[] decompress(byte[] data) throws IOException {
            return readAll(new InflaterInputStream(new ByteArrayInputStream(data)));
        }
    },

    LZMA {
        @Override
        byte[] compress(byte[] data) throws IOException {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            try (LZMACompressorOutputStream lzma = new LZMACompressorOutputStream(out)) {
                lzma.write(data);
            }
            return out.toByteArray();
        }

        @Override
        byte[] decompress(byte[] data) throws IOException {
            return readAll(new LZMACompressorInputStream(new ByteArrayInputStream(data)));
        }
    },

    LZ4 {
        @Override
        byte[] compress(byte[] data) throws IOException {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            try (FramedLZ4CompressorOutputStream lz4 = new FramedLZ4CompressorOutputStream(out)) {
                lz4.write(data);
            }
            return out.toByteArray();
        }

        @Override
        byte[] decompress(byte[] data) throws IOException {
            return readAll(new FramedLZ4CompressorInputStream(new ByteArrayInputStream(data)));
        }
    },

    BZIP2 {
        @Override
        public byte[] compress(byte[] data) throws IOException {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            try (BZip2CompressorOutputStream bzip2 = new BZip2CompressorOutputStream(out)) {
                bzip2.write(data);
            }
            return out.toByteArray();
        }

        @Override
        public byte[] decompress(byte[] data) throws IOException {
            return readAll(new BZip2CompressorInputStream(new ByteArrayInputStream(data)));
        }
    };

    abstract byte[] compress(byte[] data) throws IOException;
    abstract byte[] decompress(byte[] data) throws IOException;

    public byte[] compressSafe(byte[] data) {
        try {
            return compress(data);
        } catch (IOException e) {
            throw new RuntimeException("Compression failed using " + this.name(), e);
        }
    }

    public byte[] decompressSafe(byte[] data) {
        try {
            return decompress(data);
        } catch (IOException e) {
            throw new RuntimeException("Decompression failed using " + this.name(), e);
        }
    }

    protected byte[] readAll(InputStream is) throws IOException {
        try (is; ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            byte[] buffer = new byte[1024];
            int len;
            while ((len = is.read(buffer)) != -1) {
                out.write(buffer, 0, len);
            }
            return out.toByteArray();
        }
    }
}
