"use client";

import { Avatar, AvatarFallback, AvatarImage } from "@/components/ui/avatar";
import { Button } from "@/components/ui/button";
import {
	DropdownMenu,
	DropdownMenuContent,
	DropdownMenuItem,
	DropdownMenuLabel,
	DropdownMenuSeparator,
	DropdownMenuTrigger,
} from "@/components/ui/dropdown-menu";
import { LogOut, Search, User } from "lucide-react";
import Link from "next/link";

interface HeaderProps {
	user?: {
		name: string;
		avatar?: string;
	};
}

export function Header({ user }: HeaderProps) {
	return (
		<header className="sticky top-0 z-50 w-full border-b bg-background/95 backdrop-blur supports-[backdrop-filter]:bg-background/60">
			<div className="container flex h-16 items-center justify-between px-4">
				{/* Logo */}
				<div className="flex items-center space-x-4">
					<Link href="/home" className="flex items-center space-x-2">
						<div className="h-8 w-8 rounded-lg bg-primary flex items-center justify-center">
							<span className="text-primary-foreground font-bold text-sm">T</span>
						</div>
						<span className="font-bold text-xl">TodoApp</span>
					</Link>
				</div>

				{/* Right side */}
				<div className="flex items-center space-x-4">
					{user ? (
						<>
							{/* Search */}
							<Button variant="ghost" size="sm">
								<Search className="h-4 w-4" />
							</Button>

							{/* User Menu */}
							<DropdownMenu>
								<DropdownMenuTrigger asChild>
									<Button variant="ghost" className="relative h-8 w-8 rounded-full">
										<Avatar className="h-8 w-8">
											<AvatarImage src={user.avatar || "/placeholder.svg"} alt={user.name} />
											<AvatarFallback>{user.name.charAt(0).toUpperCase()}</AvatarFallback>
										</Avatar>
									</Button>
								</DropdownMenuTrigger>
								<DropdownMenuContent className="w-56" align="end" forceMount>
									<DropdownMenuLabel className="font-normal">
										<div className="flex flex-col space-y-1">
											<p className="text-sm font-medium leading-none">{user.name}</p>
										</div>
									</DropdownMenuLabel>
									<DropdownMenuSeparator />
									<DropdownMenuItem asChild>
										<Link href="/profile">
											<User className="mr-2 h-4 w-4" />
											Profile
										</Link>
									</DropdownMenuItem>
									<DropdownMenuSeparator />
									<DropdownMenuItem>
										<LogOut className="mr-2 h-4 w-4" />
										Log out
									</DropdownMenuItem>
								</DropdownMenuContent>
							</DropdownMenu>
						</>
					) : (
						<div className="flex items-center space-x-2">
							<Button variant="ghost" asChild>
								<Link href="/login">Sign In</Link>
							</Button>
							<Button asChild>
								<Link href="/register">Sign Up</Link>
							</Button>
						</div>
					)}
				</div>
			</div>
		</header>
	);
}
