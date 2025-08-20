import { Footer } from "@/components/layout/Footer";
import { Toaster } from "@/components/ui/sonner";
import AuthProvider from "@/providers/AuthProvider";
import "@/styles/globals.css";
import type { Metadata } from "next";
import { Inter, Poppins } from "next/font/google";

const inter = Inter({
	subsets: ["latin"],
	display: "swap",
	variable: "--font-inter",
});

const poppins = Poppins({
	subsets: ["latin"],
	display: "swap",
	weight: ["100", "200", "300", "500", "600"],
	variable: "--font-poppins",
});

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
			<AuthProvider>
				<body className={`${poppins.variable} ${inter.variable}`}>
					<Toaster richColors position="top-right" />
					{children}
					<Footer />
				</body>
			</AuthProvider>
		</html>
	);
}
