import Link from "next/link";

export function Footer() {
	return (
		<footer className="border-t bg-background">
			<div className="container mx-auto px-4 py-8">
				<div className="grid grid-cols-1 md:grid-cols-4 gap-8">
					{/* Brand */}
					<div className="space-y-4">
						<div className="flex items-center space-x-2">
							<div className="h-8 w-8 rounded-lg bg-primary flex items-center justify-center">
								<span className="text-primary-foreground font-bold text-sm">T</span>
							</div>
							<span className="font-bold text-xl">TodoApp</span>
						</div>
						<p className="text-sm text-muted-foreground">
							Modern task management solution for individuals and teams.
						</p>
					</div>

					{/* Product */}
					<div className="space-y-4">
						<h3 className="font-semibold">Product</h3>
						<ul className="space-y-2 text-sm">
							<li>
								<Link href="#" className="text-muted-foreground hover:text-foreground">
									Features
								</Link>
							</li>
							<li>
								<Link href="#" className="text-muted-foreground hover:text-foreground">
									Pricing
								</Link>
							</li>
							<li>
								<Link href="#" className="text-muted-foreground hover:text-foreground">
									Integrations
								</Link>
							</li>
						</ul>
					</div>

					{/* Support */}
					<div className="space-y-4">
						<h3 className="font-semibold">Support</h3>
						<ul className="space-y-2 text-sm">
							<li>
								<Link href="#" className="text-muted-foreground hover:text-foreground">
									Help Center
								</Link>
							</li>
							<li>
								<Link href="#" className="text-muted-foreground hover:text-foreground">
									Contact Us
								</Link>
							</li>
							<li>
								<Link href="#" className="text-muted-foreground hover:text-foreground">
									Status
								</Link>
							</li>
						</ul>
					</div>

					{/* Legal */}
					<div className="space-y-4">
						<h3 className="font-semibold">Legal</h3>
						<ul className="space-y-2 text-sm">
							<li>
								<Link href="#" className="text-muted-foreground hover:text-foreground">
									Privacy Policy
								</Link>
							</li>
							<li>
								<Link href="#" className="text-muted-foreground hover:text-foreground">
									Terms of Service
								</Link>
							</li>
							<li>
								<Link href="#" className="text-muted-foreground hover:text-foreground">
									Cookie Policy
								</Link>
							</li>
						</ul>
					</div>
				</div>

				<div className="mt-8 pt-8 border-t">
					<div className="flex flex-col md:flex-row justify-between items-center">
						<p className="text-sm text-muted-foreground">
							© {new Date().getFullYear().toString()} TodoApp. All rights reserved.
						</p>
						<div className="flex items-center space-x-4 mt-4 md:mt-0">
							<Link href="#" className="text-sm text-muted-foreground hover:text-foreground">
								Privacy
							</Link>
							<Link href="#" className="text-sm text-muted-foreground hover:text-foreground">
								Terms
							</Link>
						</div>
					</div>
				</div>
			</div>
		</footer>
	);
}
