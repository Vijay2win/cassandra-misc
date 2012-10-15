package com.vijay2win;

import java.math.BigInteger;

public class TokenCalculator {
    public static final BigInteger MIN_BIGINT_TOKEN = new BigInteger("0");
    public static final BigInteger MAX_BIGINT_TOKEN = new BigInteger("2").pow(127);
    public static final BigInteger MAX_LONG_TOKEN = new BigInteger("" + Long.MAX_VALUE).multiply(new BigInteger("2"));

    public static void main(String[] args) {
        if (args.length == 0 || args[0].equals("-h")) {
            System.out.println("Usage: <command> <num nodes in a region> <optional: region>");
            System.exit(1);
        }
        int size = Integer.parseInt(args[0]);
        String region = args.length > 1 ? args[1] : "";
        System.out.println("For RandomPartitioner\n");
        for (int i = 0; i < Integer.parseInt(args[0]); i++)
            System.out.println("Node " + i + "\t: " + createToken(i, size, region));

        System.out.println("\nFor Murmur3Partitioner\n");
        for (int i = 0; i < Integer.parseInt(args[0]); i++)
            System.out.println("Node " + i + "\t: " + createLongToken(i, size, region));
    }

    private static String createLongToken(int position, int size, String region) {
        return new BigInteger(MAX_LONG_TOKEN.toString()).divide(new BigInteger("" + size))
                .multiply(new BigInteger("" + position)).add(BigInteger.valueOf(regionOffset(region))).longValue()
                + "";
    }

    private static BigInteger createToken(int position, int size, String region) {
        return MAX_BIGINT_TOKEN.divide(BigInteger.valueOf(size)).multiply(BigInteger.valueOf(position))
                .add(BigInteger.valueOf(regionOffset(region)));
    }

    public static int regionOffset(String region) {
        return Math.abs(region.hashCode());
    }
}
