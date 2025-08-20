import { Button } from "@/components/ui/button";
import { Card, CardContent, CardDescription, CardHeader, CardTitle } from "@/components/ui/card";
import { CheckCircle, Shield, Users, Zap } from "lucide-react";
import Link from "next/link";

export default function HomePage() {
	return (
		<div className="container mx-auto px-4 py-16">
			{/* Hero Section */}
			<div className="text-center mb-16">
				<h1 className="text-5xl font-bold text-foreground mb-6">TodoApp</h1>
				<p className="text-xl text-muted-foreground mb-8 max-w-2xl mx-auto">
					A modern, clean task management solution with powerful user and admin dashboards. Stay organized,
					boost productivity, and manage your team effectively.
				</p>
				<div className="flex gap-4 justify-center">
					<Button asChild size="lg">
						<Link href="/register">Get Started</Link>
					</Button>
					<Button variant="outline" asChild size="lg">
						<Link href="/login">Sign In</Link>
					</Button>
				</div>
			</div>

			{/* Features Grid */}
			<div className="grid md:grid-cols-2 lg:grid-cols-3 gap-6 mb-16">
				<Card>
					<CardHeader>
						<CheckCircle className="h-8 w-8 text-primary mb-2" />
						<CardTitle>Task Management</CardTitle>
						<CardDescription>
							Create, organize, and track your tasks with an intuitive interface
						</CardDescription>
					</CardHeader>
				</Card>

				<Card>
					<CardHeader>
						<Users className="h-8 w-8 text-primary mb-2" />
						<CardTitle>User Dashboard</CardTitle>
						<CardDescription>Personal workspace to manage your tasks and track progress</CardDescription>
					</CardHeader>
				</Card>

				<Card>
					<CardHeader>
						<Shield className="h-8 w-8 text-primary mb-2" />
						<CardTitle>Admin Control</CardTitle>
						<CardDescription>Comprehensive admin dashboard for user and system management</CardDescription>
					</CardHeader>
				</Card>

				<Card>
					<CardHeader>
						<Zap className="h-8 w-8 text-primary mb-2" />
						<CardTitle>Modern Design</CardTitle>
						<CardDescription>
							Clean, responsive interface that works beautifully on all devices
						</CardDescription>
					</CardHeader>
				</Card>

				<Card>
					<CardHeader>
						<CheckCircle className="h-8 w-8 text-primary mb-2" />
						<CardTitle>Profile Management</CardTitle>
						<CardDescription>Easy-to-use profile settings and customization options</CardDescription>
					</CardHeader>
				</Card>

				<Card>
					<CardHeader>
						<Shield className="h-8 w-8 text-primary mb-2" />
						<CardTitle>Secure Authentication</CardTitle>
						<CardDescription>Robust login and registration system with modern security</CardDescription>
					</CardHeader>
				</Card>
			</div>

			{/* CTA Section */}
			<div className="text-center">
				<Card className="max-w-2xl mx-auto">
					<CardHeader>
						<CardTitle className="text-2xl">Ready to Get Organized?</CardTitle>
						<CardDescription className="text-lg">
							Join thousands of users who have transformed their productivity with TodoApp
						</CardDescription>
					</CardHeader>
					<CardContent>
						<Button asChild size="lg" className="w-full sm:w-auto">
							<Link href="/register">Start Your Journey</Link>
						</Button>
					</CardContent>
				</Card>
			</div>
		</div>
	);
}
