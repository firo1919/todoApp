export type LoginResponse = {
	status: true;
	message: string;
	data: {
		id: string;
		username: string;
		active: true;
		role: string;
		accessToken: string;
		refreshToken: string;
	};
};

export type RefreshTokenResponse = {
	status: true;
	message: string;
	data: {
		id: string;
		username: string;
		active: true;
		role: string;
		accessToken: string;
		refreshToken: string;
	};
};

export type RefreshToken = {
	refreshToken: string;
};
