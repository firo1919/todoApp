import "@/styles/globals.css";
import type { Metadata } from "next";

export const metadata: Metadata = {
	title: "YourTodo",
	description: "A simple todo application built for practicing nextjs",
};

export default function RootLayout({
	children,
}: Readonly<{
	children: React.ReactNode;
}>) {
	return (
		<html lang="en">
			<body>{children}</body>
		</html>
	);
}
