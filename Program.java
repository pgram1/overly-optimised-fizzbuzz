// overly-optimised fizzbuzz

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.LinkedList;

public class Program {

	public static void main(String[] args) throws IOException {

		// output message in case the user does not input a range
		if (args.length < 2) {
			System.out.println("Please input a range for the fast FizzBuzz utility");
			System.exit(0);
		}

		long start = Long.parseLong(args[0]);
		// +1 so we get the correct range in the loop below without the need for the
		// equals check
		long finish = Long.parseLong(args[1]) + 1;

		// the mod 15 check is skipped by using a flag boolean
		boolean check;
		LinkedList<String> result = new LinkedList<String>();
		for (long i = start; i < finish; i++) {
			check = true;
			if (i % 3 == 0) {
				result.add("Fizz");
				check = false;
			}
			if (i % 5 == 0) {
				result.add("Buzz");
				check = false;
			}
			if (check) {
				// faster than parsing long to string
				result.add("" + i);
			}
			result.add("\n");
		}

		byte[] strBytes = resultToString(result).getBytes();

		RandomAccessFile stream = new RandomAccessFile("out.log", "rw");
		// a faster IO export methodology
		FileChannel channel = stream.getChannel();
		ByteBuffer buffer = ByteBuffer.allocate(strBytes.length);
		buffer.put(strBytes);
		buffer.flip();
		channel.write(buffer);
		stream.close();
		channel.close();

		System.exit(0);

	}

	// maintains the performance of the toString of a LinkedList but removes extra
	// characters
	private static String resultToString(LinkedList<String> result) {
		String newResult = result.toString().replaceAll("[,\\[\\] ]", "");
		return newResult;
	}

}
