export default async function UserHome({ params }: { params: Promise<{ username: string }> }) {
	const { username } = await params;
	return (
		<div className="">
			<h1>Welcome {username}</h1>
			<p>User</p>
		</div>
	);
}
