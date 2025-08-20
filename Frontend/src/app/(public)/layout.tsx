import { Header } from "@/components/layout/Header";
import "@/styles/globals.css";

export default function RootLayout({
	children,
}: Readonly<{
	children: React.ReactNode;
}>) {
	return (
		<div className="">
			<Header />
			{children}
		</div>
	);
}
